import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUmfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from './umfrage.service';

@Component({
  templateUrl: './umfrage-delete-dialog.component.html',
})
export class UmfrageDeleteDialogComponent {
  umfrage?: IUmfrage;

  constructor(protected umfrageService: UmfrageService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.umfrageService.delete(id).subscribe(() => {
      this.eventManager.broadcast('umfrageListModification');
      this.activeModal.close();
    });
  }
}
