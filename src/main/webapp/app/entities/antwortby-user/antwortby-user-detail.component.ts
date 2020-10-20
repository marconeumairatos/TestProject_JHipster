import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAntwortbyUser } from 'app/shared/model/antwortby-user.model';

@Component({
  selector: 'jhi-antwortby-user-detail',
  templateUrl: './antwortby-user-detail.component.html',
})
export class AntwortbyUserDetailComponent implements OnInit {
  antwortbyUser: IAntwortbyUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antwortbyUser }) => (this.antwortbyUser = antwortbyUser));
  }

  previousState(): void {
    window.history.back();
  }
}
