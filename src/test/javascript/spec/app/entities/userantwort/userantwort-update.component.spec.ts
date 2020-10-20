import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UserantwortUpdateComponent } from 'app/entities/userantwort/userantwort-update.component';
import { UserantwortService } from 'app/entities/userantwort/userantwort.service';
import { Userantwort } from 'app/shared/model/userantwort.model';

describe('Component Tests', () => {
  describe('Userantwort Management Update Component', () => {
    let comp: UserantwortUpdateComponent;
    let fixture: ComponentFixture<UserantwortUpdateComponent>;
    let service: UserantwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserantwortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UserantwortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserantwortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserantwortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Userantwort(123);
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
        const entity = new Userantwort();
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
