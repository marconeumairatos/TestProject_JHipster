import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAntwortbyUser, AntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { AntwortbyUserService } from './antwortby-user.service';
import { AntwortbyUserComponent } from './antwortby-user.component';
import { AntwortbyUserDetailComponent } from './antwortby-user-detail.component';
import { AntwortbyUserUpdateComponent } from './antwortby-user-update.component';

@Injectable({ providedIn: 'root' })
export class AntwortbyUserResolve implements Resolve<IAntwortbyUser> {
  constructor(private service: AntwortbyUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntwortbyUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((antwortbyUser: HttpResponse<AntwortbyUser>) => {
          if (antwortbyUser.body) {
            return of(antwortbyUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AntwortbyUser());
  }
}

export const antwortbyUserRoute: Routes = [
  {
    path: '',
    component: AntwortbyUserComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwortbyUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AntwortbyUserDetailComponent,
    resolve: {
      antwortbyUser: AntwortbyUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwortbyUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AntwortbyUserUpdateComponent,
    resolve: {
      antwortbyUser: AntwortbyUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwortbyUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AntwortbyUserUpdateComponent,
    resolve: {
      antwortbyUser: AntwortbyUserResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwortbyUser.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
