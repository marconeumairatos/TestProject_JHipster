import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserantwort } from 'app/shared/model/userantwort.model';

@Component({
  selector: 'jhi-userantwort-detail',
  templateUrl: './userantwort-detail.component.html',
})
export class UserantwortDetailComponent implements OnInit {
  userantwort: IUserantwort | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userantwort }) => (this.userantwort = userantwort));
  }

  previousState(): void {
    window.history.back();
  }
}
