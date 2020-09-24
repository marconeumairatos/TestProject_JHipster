import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAntwort, Antwort } from 'app/shared/model/antwort.model';
import { AntwortService } from './antwort.service';
import { IUmfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from 'app/entities/umfrage/umfrage.service';

@Component({
  selector: 'jhi-antwort-update',
  templateUrl: './antwort-update.component.html',
})
export class AntwortUpdateComponent implements OnInit {
  isSaving = false;
  umfrages: IUmfrage[] = [];

  editForm = this.fb.group({
    id: [],
    pollID: [null, [Validators.required]],
    text: [null, [Validators.required]],
    umfrage: [],
  });

  constructor(
    protected antwortService: AntwortService,
    protected umfrageService: UmfrageService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ antwort }) => {
      this.updateForm(antwort);

      this.umfrageService.query().subscribe((res: HttpResponse<IUmfrage[]>) => (this.umfrages = res.body || []));
    });
  }

  updateForm(antwort: IAntwort): void {
    this.editForm.patchValue({
      id: antwort.id,
      pollID: antwort.pollID,
      text: antwort.text,
      umfrage: antwort.umfrage,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const antwort = this.createFromForm();
    if (antwort.id !== undefined) {
      this.subscribeToSaveResponse(this.antwortService.update(antwort));
    } else {
      this.subscribeToSaveResponse(this.antwortService.create(antwort));
    }
  }

  private createFromForm(): IAntwort {
    return {
      ...new Antwort(),
      id: this.editForm.get(['id'])!.value,
      pollID: this.editForm.get(['pollID'])!.value,
      text: this.editForm.get(['text'])!.value,
      umfrage: this.editForm.get(['umfrage'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAntwort>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IUmfrage): any {
    return item.id;
  }
}
