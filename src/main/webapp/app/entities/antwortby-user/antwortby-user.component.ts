import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';
import { AntwortbyUserService } from './antwortby-user.service';
import { AntwortbyUserDeleteDialogComponent } from './antwortby-user-delete-dialog.component';

@Component({
  selector: 'jhi-antwortby-user',
  templateUrl: './antwortby-user.component.html',
})
export class AntwortbyUserComponent implements OnInit, OnDestroy {
  antwortbyUsers?: IAntwortbyUser[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected antwortbyUserService: AntwortbyUserService,
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
      this.antwortbyUserService
        .search({
          query: this.currentSearch,
        })
        .subscribe((res: HttpResponse<IAntwortbyUser[]>) => (this.antwortbyUsers = res.body || []));
      return;
    }

    this.antwortbyUserService.query().subscribe((res: HttpResponse<IAntwortbyUser[]>) => (this.antwortbyUsers = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAntwortbyUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAntwortbyUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAntwortbyUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('antwortbyUserListModification', () => this.loadAll());
  }

  delete(antwortbyUser: IAntwortbyUser): void {
    const modalRef = this.modalService.open(AntwortbyUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.antwortbyUser = antwortbyUser;
  }
}
