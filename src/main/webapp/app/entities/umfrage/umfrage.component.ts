import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUmfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from './umfrage.service';
import { UmfrageDeleteDialogComponent } from './umfrage-delete-dialog.component';

@Component({
  selector: 'jhi-umfrage',
  templateUrl: './umfrage.component.html',
})
export class UmfrageComponent implements OnInit, OnDestroy {
  umfrages?: IUmfrage[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected umfrageService: UmfrageService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.umfrageService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IUmfrage[]>) => (this.umfrages = res.body || []));
      return;
    }

    this.umfrageService.query().subscribe((res: HttpResponse<IUmfrage[]>) => (this.umfrages = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUmfrages();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUmfrage): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUmfrages(): void {
    this.eventSubscriber = this.eventManager.subscribe('umfrageListModification', () => this.loadAll());
  }

  delete(umfrage: IUmfrage): void {
    const modalRef = this.modalService.open(UmfrageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.umfrage = umfrage;
  }
}
