import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { AntwortbyUserService } from './antwortby-user.service';

@Component({
  templateUrl: './antwortby-user-delete-dialog.component.html',
})
export class AntwortbyUserDeleteDialogComponent {
  antwortbyUser?: IAntwortbyUser;

  constructor(
    protected antwortbyUserService: AntwortbyUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antwortbyUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('antwortbyUserListModification');
      this.activeModal.close();
    });
  }
}
