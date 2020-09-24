import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { UmfrageUpdateComponent } from 'app/entities/umfrage/umfrage-update.component';
import { UmfrageService } from 'app/entities/umfrage/umfrage.service';
import { Umfrage } from 'app/shared/model/umfrage.model';

describe('Component Tests', () => {
  describe('Umfrage Management Update Component', () => {
    let comp: UmfrageUpdateComponent;
    let fixture: ComponentFixture<UmfrageUpdateComponent>;
    let service: UmfrageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UmfrageUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(UmfrageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UmfrageUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UmfrageService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Umfrage(123);
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
        const entity = new Umfrage();
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
