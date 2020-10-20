import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserantwort } from 'app/shared/model/userantwort.model';
import { UserantwortService } from './userantwort.service';

@Component({
  templateUrl: './userantwort-delete-dialog.component.html',
})
export class UserantwortDeleteDialogComponent {
  userantwort?: IUserantwort;

  constructor(
    protected userantwortService: UserantwortService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userantwortService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userantwortListModification');
      this.activeModal.close();
    });
  }
}
