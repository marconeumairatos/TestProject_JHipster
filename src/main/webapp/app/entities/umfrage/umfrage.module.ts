import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JpollSharedModule } from 'app/shared/shared.module';
import { UmfrageComponent } from './umfrage.component';
import { UmfrageDetailComponent } from './umfrage-detail.component';
import { UmfrageUpdateComponent } from './umfrage-update.component';
import { UmfrageDeleteDialogComponent } from './umfrage-delete-dialog.component';
import { umfrageRoute } from './umfrage.route';

@NgModule({
  imports: [JpollSharedModule, RouterModule.forChild(umfrageRoute)],
  declarations: [UmfrageComponent, UmfrageDetailComponent, UmfrageUpdateComponent, UmfrageDeleteDialogComponent],
  entryComponents: [UmfrageDeleteDialogComponent],
})
export class JpollUmfrageModule {}
