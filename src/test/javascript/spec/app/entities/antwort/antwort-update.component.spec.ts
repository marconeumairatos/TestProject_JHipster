import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { AntwortUpdateComponent } from 'app/entities/antwort/antwort-update.component';
import { AntwortService } from 'app/entities/antwort/antwort.service';
import { Antwort } from 'app/shared/model/antwort.model';

describe('Component Tests', () => {
  describe('Antwort Management Update Component', () => {
    let comp: AntwortUpdateComponent;
    let fixture: ComponentFixture<AntwortUpdateComponent>;
    let service: AntwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [AntwortUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AntwortUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AntwortUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AntwortService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Antwort(123);
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
        const entity = new Antwort();
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
