import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JpollSharedModule } from 'app/shared/shared.module';
import { AntwortbyUserComponent } from './antwortby-user.component';
import { AntwortbyUserDetailComponent } from './antwortby-user-detail.component';
import { AntwortbyUserUpdateComponent } from './antwortby-user-update.component';
import { AntwortbyUserDeleteDialogComponent } from './antwortby-user-delete-dialog.component';
import { antwortbyUserRoute } from './antwortby-user.route';

@NgModule({
  imports: [JpollSharedModule, RouterModule.forChild(antwortbyUserRoute)],
  declarations: [AntwortbyUserComponent, AntwortbyUserDetailComponent, AntwortbyUserUpdateComponent, AntwortbyUserDeleteDialogComponent],
  entryComponents: [AntwortbyUserDeleteDialogComponent],
})
export class JpollAntwortbyUserModule {}
