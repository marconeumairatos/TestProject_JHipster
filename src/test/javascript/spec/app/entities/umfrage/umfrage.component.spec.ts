import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JpollTestModule } from '../../../test.module';
import { UmfrageComponent } from 'app/entities/umfrage/umfrage.component';
import { UmfrageService } from 'app/entities/umfrage/umfrage.service';
import { Umfrage } from 'app/shared/model/umfrage.model';

describe('Component Tests', () => {
  describe('Umfrage Management Component', () => {
    let comp: UmfrageComponent;
    let fixture: ComponentFixture<UmfrageComponent>;
    let service: UmfrageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UmfrageComponent],
      })
        .overrideTemplate(UmfrageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UmfrageComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UmfrageService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Umfrage(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.umfrages && comp.umfrages[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
