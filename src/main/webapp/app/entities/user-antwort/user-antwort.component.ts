import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserAntwort } from 'app/shared/model/user-antwort.model';
import { UserAntwortService } from './user-antwort.service';
import { UserAntwortDeleteDialogComponent } from './user-antwort-delete-dialog.component';

@Component({
  selector: 'jhi-user-antwort',
  templateUrl: './user-antwort.component.html',
})
export class UserAntwortComponent implements OnInit, OnDestroy {
  userAntworts?: IUserAntwort[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected userAntwortService: UserAntwortService,
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
      this.userAntwortService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IUserAntwort[]>) => (this.userAntworts = res.body || []));
      return;
    }

    this.userAntwortService.query().subscribe((res: HttpResponse<IUserAntwort[]>) => (this.userAntworts = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserAntworts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserAntwort): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserAntworts(): void {
    this.eventSubscriber = this.eventManager.subscribe('userAntwortListModification', () => this.loadAll());
  }

  delete(userAntwort: IUserAntwort): void {
    const modalRef = this.modalService.open(UserAntwortDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userAntwort = userAntwort;
  }
}
