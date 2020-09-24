import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from './antwort.service';
import { AntwortDeleteDialogComponent } from './antwort-delete-dialog.component';

@Component({
  selector: 'jhi-antwort',
  templateUrl: './antwort.component.html',
})
export class AntwortComponent implements OnInit, OnDestroy {
  antworts?: IAntwort[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected antwortService: AntwortService,
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
      this.antwortService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
      return;
    }

    this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAntworts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAntwort): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAntworts(): void {
    this.eventSubscriber = this.eventManager.subscribe('antwortListModification', () => this.loadAll());
  }

  delete(antwort: IAntwort): void {
    const modalRef = this.modalService.open(AntwortDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.antwort = antwort;
  }
}
