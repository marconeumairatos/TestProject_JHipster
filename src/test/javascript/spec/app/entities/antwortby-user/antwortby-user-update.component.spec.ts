import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { JpollTestModule } from '../../../test.module';
import { AntwortbyUserUpdateComponent } from 'app/entities/antwortby-user/antwortby-user-update.component';
import { AntwortbyUserService } from 'app/entities/antwortby-user/antwortby-user.service';
import { AntwortbyUser } from 'app/shared/model/antwortby-user.model';

describe('Component Tests', () => {
  describe('AntwortbyUser Management Update Component', () => {
    let comp: AntwortbyUserUpdateComponent;
    let fixture: ComponentFixture<AntwortbyUserUpdateComponent>;
    let service: AntwortbyUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [AntwortbyUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AntwortbyUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AntwortbyUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AntwortbyUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AntwortbyUser(123);
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
        const entity = new AntwortbyUser();
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
