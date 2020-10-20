import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserantwort, Userantwort } from 'app/shared/model/userantwort.model';
import { UserantwortService } from './userantwort.service';
import { IUmfrage } from 'app/shared/model/umfrage.model';
import { UmfrageService } from 'app/entities/umfrage/umfrage.service';
import { IAntwort } from 'app/shared/model/antwort.model';
import { AntwortService } from 'app/entities/antwort/antwort.service';

type SelectableEntity = IUmfrage | IAntwort;

@Component({
  selector: 'jhi-userantwort-update',
  templateUrl: './userantwort-update.component.html',
})
export class UserantwortUpdateComponent implements OnInit {
  isSaving = false;
  umfrages: IUmfrage[] = [];
  antworts: IAntwort[] = [];

  editForm = this.fb.group({
    id: [],
    user: [null, [Validators.required]],
    umfrage: [],
    antwort: [],
  });

  constructor(
    protected userantwortService: UserantwortService,
    protected umfrageService: UmfrageService,
    protected antwortService: AntwortService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userantwort }) => {
      this.updateForm(userantwort);

      this.umfrageService.query().subscribe((res: HttpResponse<IUmfrage[]>) => (this.umfrages = res.body || []));

      this.antwortService.query().subscribe((res: HttpResponse<IAntwort[]>) => (this.antworts = res.body || []));
    });
  }

  updateForm(userantwort: IUserantwort): void {
    this.editForm.patchValue({
      id: userantwort.id,
      user: userantwort.user,
      umfrage: userantwort.umfrage,
      antwort: userantwort.antwort,
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
      user: this.editForm.get(['user'])!.value,
      umfrage: this.editForm.get(['umfrage'])!.value,
      antwort: this.editForm.get(['antwort'])!.value,
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
