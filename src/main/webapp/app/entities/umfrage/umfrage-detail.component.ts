import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUmfrage } from 'app/shared/model/umfrage.model';

@Component({
  selector: 'jhi-umfrage-detail',
  templateUrl: './umfrage-detail.component.html',
})
export class UmfrageDetailComponent implements OnInit {
  umfrage: IUmfrage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umfrage }) => (this.umfrage = umfrage));
  }

  previousState(): void {
    window.history.back();
  }
}
