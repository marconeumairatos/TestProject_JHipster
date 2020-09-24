import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUmfrage, Umfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from './umfrage.service';
import { UmfrageComponent } from './umfrage.component';
import { UmfrageDetailComponent } from './umfrage-detail.component';
import { UmfrageUpdateComponent } from './umfrage-update.component';

@Injectable({ providedIn: 'root' })
export class UmfrageResolve implements Resolve<IUmfrage> {
  constructor(private service: UmfrageService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUmfrage> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((umfrage: HttpResponse<Umfrage>) => {
          if (umfrage.body) {
            return of(umfrage.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Umfrage());
  }
}

export const umfrageRoute: Routes = [
  {
    path: '',
    component: UmfrageComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.umfrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: UmfrageDetailComponent,
    resolve: {
      umfrage: UmfrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.umfrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: UmfrageUpdateComponent,
    resolve: {
      umfrage: UmfrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.umfrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: UmfrageUpdateComponent,
    resolve: {
      umfrage: UmfrageResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jpollApp.umfrage.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
