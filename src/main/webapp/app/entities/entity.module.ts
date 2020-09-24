import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'umfrage',
        loadChildren: () => import('./umfrage/umfrage.module').then(m => m.JpollUmfrageModule),
      },
      {
        path: 'antwort',
        loadChildren: () => import('./antwort/antwort.module').then(m => m.JpollAntwortModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JpollEntityModule {}
