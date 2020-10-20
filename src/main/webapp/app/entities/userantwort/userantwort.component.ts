import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserantwort } from 'app/shared/model/userantwort.model';
import { UserantwortService } from './userantwort.service';
import { UserantwortDeleteDialogComponent } from './userantwort-delete-dialog.component';

@Component({
  selector: 'jhi-userantwort',
  templateUrl: './userantwort.component.html',
})
export class UserantwortComponent implements OnInit, OnDestroy {
  userantworts?: IUserantwort[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected userantwortService: UserantwortService,
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
      this.userantwortService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IUserantwort[]>) => (this.userantworts = res.body || []));
      return;
    }

    this.userantwortService.query().subscribe((res: HttpResponse<IUserantwort[]>) => (this.userantworts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserantworts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserantwort): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserantworts(): void {
    this.eventSubscriber = this.eventManager.subscribe('userantwortListModification', () => this.loadAll());
  }

  delete(userantwort: IUserantwort): void {
    const modalRef = this.modalService.open(UserantwortDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userantwort = userantwort;
  }
}
