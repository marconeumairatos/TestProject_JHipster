import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JpollSharedModule } from 'app/shared/shared.module';
import { AntwortComponent } from './antwort.component';
import { AntwortDetailComponent } from './antwort-detail.component';
import { AntwortUpdateComponent } from './antwort-update.component';
import { AntwortDeleteDialogComponent } from './antwort-delete-dialog.component';
import { antwortRoute } from './antwort.route';

@NgModule({
  imports: [JpollSharedModule, RouterModule.forChild(antwortRoute)],
  declarations: [AntwortComponent, AntwortDetailComponent, AntwortUpdateComponent, AntwortDeleteDialogComponent],
  entryComponents: [AntwortDeleteDialogComponent],
})
export class JpollAntwortModule {}
