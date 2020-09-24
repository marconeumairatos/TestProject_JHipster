import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from './antwort.service';

@Component({
  templateUrl: './antwort-delete-dialog.component.html',
})
export class AntwortDeleteDialogComponent {
  antwort?: IAntwort;

  constructor(protected antwortService: AntwortService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.antwortService.delete(id).subscribe(() => {
      this.eventManager.broadcast('antwortListModification');
      this.activeModal.close();
    });
  }
}
