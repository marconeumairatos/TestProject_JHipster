import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUmfrage, Umfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from './umfrage.service';

@Component({
  selector: 'jhi-umfrage-update',
  templateUrl: './umfrage-update.component.html',
})
export class UmfrageUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    text: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(protected umfrageService: UmfrageService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ umfrage }) => {
      this.updateForm(umfrage);
    });
  }

  updateForm(umfrage: IUmfrage): void {
    this.editForm.patchValue({
      id: umfrage.id,
      name: umfrage.name,
      text: umfrage.text,
      status: umfrage.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const umfrage = this.createFromForm();
    if (umfrage.id !== undefined) {
      this.subscribeToSaveResponse(this.umfrageService.update(umfrage));
    } else {
      this.subscribeToSaveResponse(this.umfrageService.create(umfrage));
    }
  }

  private createFromForm(): IUmfrage {
    return {
      ...new Umfrage(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      text: this.editForm.get(['text'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUmfrage>>): void {
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
}
