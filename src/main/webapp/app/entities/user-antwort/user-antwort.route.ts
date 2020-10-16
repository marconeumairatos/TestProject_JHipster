import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserAntwort, UserAntwort } from 'app/shared/model/user-antwort.model';
import { UserAntwortService } from './user-antwort.service';
import { UserAntwortComponent } from './user-antwort.component';
import { UserAntwortDetailComponent } from './user-antwort-detail.component';
import { UserAntwortUpdateComponent } from './user-antwort-update.component';

@Injectable({ providedIn: 'root' })
export class UserAntwortResolve implements Resolve<IUserAntwort> {
  constructor(private service: UserAntwortService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserAntwort> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userAntwort: HttpResponse<UserAntwort>) => {
          if (userAntwort.body) {
            return of(userAntwort.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserAntwort());
  }
}

export const userAntwortRoute: Routes = [
  {
    path: '',
    component: UserAntwortComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.userAntwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UserAntwortDetailComponent,
    resolve: {
      userAntwort: UserAntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.userAntwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UserAntwortUpdateComponent,
    resolve: {
      userAntwort: UserAntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.userAntwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UserAntwortUpdateComponent,
    resolve: {
      userAntwort: UserAntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.userAntwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
