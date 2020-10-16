import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UserAntwortUpdateComponent } from 'app/entities/user-antwort/user-antwort-update.component';
import { UserAntwortService } from 'app/entities/user-antwort/user-antwort.service';
import { UserAntwort } from 'app/shared/model/user-antwort.model';

describe('Component Tests', () => {
  describe('UserAntwort Management Update Component', () => {
    let comp: UserAntwortUpdateComponent;
    let fixture: ComponentFixture<UserAntwortUpdateComponent>;
    let service: UserAntwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserAntwortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserAntwortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAntwortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAntwortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAntwort(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new UserAntwort();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
