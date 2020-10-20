import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JpollSharedModule } from 'app/shared/shared.module';
import { UserantwortComponent } from './userantwort.component';
import { UserantwortDetailComponent } from './userantwort-detail.component';
import { UserantwortUpdateComponent } from './userantwort-update.component';
import { UserantwortDeleteDialogComponent } from './userantwort-delete-dialog.component';
import { userantwortRoute } from './userantwort.route';

@NgModule({
  imports: [JpollSharedModule, RouterModule.forChild(userantwortRoute)],
  declarations: [UserantwortComponent, UserantwortDetailComponent, UserantwortUpdateComponent, UserantwortDeleteDialogComponent],
  entryComponents: [UserantwortDeleteDialogComponent],
})
export class JpollUserantwortModule {}
