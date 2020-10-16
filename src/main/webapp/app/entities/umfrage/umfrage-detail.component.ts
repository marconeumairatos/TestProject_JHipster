import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';

import { IUmfrage } from 'app/shared/model/umfrage.model';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';
@Component({
  selector: 'jhi-umfrage-detail',
  templateUrl: './umfrage-detail.component.html',
})
export class UmfrageDetailComponent implements OnInit {
  umfrage: IUmfrage | null = null;
  antworts?: IAntwort[];

  constructor(protected activatedRoute: ActivatedRoute, protected antwortService: AntwortService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umfrage }) => (this.umfrage = umfrage));
    this.loadAll();
  }

  previousState(): void {
    window.history.back();
  }

  loadAll(): void {
    this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
  }
}
