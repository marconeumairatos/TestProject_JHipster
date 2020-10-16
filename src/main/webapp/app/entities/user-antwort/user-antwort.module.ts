import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JpollSharedModule } from 'app/shared/shared.module';
import { UserAntwortComponent } from './user-antwort.component';
import { UserAntwortDetailComponent } from './user-antwort-detail.component';
import { UserAntwortUpdateComponent } from './user-antwort-update.component';
import { UserAntwortDeleteDialogComponent } from './user-antwort-delete-dialog.component';
import { userAntwortRoute } from './user-antwort.route';

@NgModule({
  imports: [JpollSharedModule, RouterModule.forChild(userAntwortRoute)],
  declarations: [UserAntwortComponent, UserAntwortDetailComponent, UserAntwortUpdateComponent, UserAntwortDeleteDialogComponent],
  entryComponents: [UserAntwortDeleteDialogComponent],
})
export class JpollUserAntwortModule {}
