import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAntwort, Antwort } from 'app/shared/model/antwort.model';
import { AntwortService } from './antwort.service';
import { AntwortComponent } from './antwort.component';
import { AntwortDetailComponent } from './antwort-detail.component';
import { AntwortUpdateComponent } from './antwort-update.component';

@Injectable({ providedIn: 'root' })
export class AntwortResolve implements Resolve<IAntwort> {
  constructor(private service: AntwortService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAntwort> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((antwort: HttpResponse<Antwort>) => {
          if (antwort.body) {
            return of(antwort.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Antwort());
  }
}

export const antwortRoute: Routes = [
  {
    path: '',
    component: AntwortComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AntwortDetailComponent,
    resolve: {
      antwort: AntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AntwortUpdateComponent,
    resolve: {
      antwort: AntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AntwortUpdateComponent,
    resolve: {
      antwort: AntwortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.antwort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
