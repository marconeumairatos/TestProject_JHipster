import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserantwort, Userantwort } from 'app/shared/model/userantwort.model';
import { UserantwortService } from './userantwort.service';

@Component({
  selector: 'jhi-userantwort-update',
  templateUrl: './userantwort-update.component.html',
})
export class UserantwortUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    userID: [null, [Validators.required]],
  });

  constructor(protected userantwortService: UserantwortService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userantwort }) => {
      this.updateForm(userantwort);
    });
  }

  updateForm(userantwort: IUserantwort): void {
    this.editForm.patchValue({
      id: userantwort.id,
      userID: userantwort.userID,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userantwort = this.createFromForm();
    if (userantwort.id !== undefined) {
      this.subscribeToSaveResponse(this.userantwortService.update(userantwort));
    } else {
      this.subscribeToSaveResponse(this.userantwortService.create(userantwort));
    }
  }

  private createFromForm(): IUserantwort {
    return {
      ...new Userantwort(),
      id: this.editForm.get(['id'])!.value,
      userID: this.editForm.get(['userID'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserantwort>>): void {
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
