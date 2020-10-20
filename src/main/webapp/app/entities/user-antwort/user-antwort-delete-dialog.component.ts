import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserAntwort } from 'app/shared/model/user-antwort.model';
import { UserAntwortService } from './user-antwort.service';

@Component({
  templateUrl: './user-antwort-delete-dialog.component.html',
})
export class UserAntwortDeleteDialogComponent {
  userAntwort?: IUserAntwort;

  constructor(
    protected userAntwortService: UserAntwortService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userAntwortService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userAntwortListModification');
      this.activeModal.close();
    });
  }
}
