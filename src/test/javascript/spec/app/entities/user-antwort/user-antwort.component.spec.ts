import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JpollTestModule } from '../../../test.module';
import { UserAntwortComponent } from 'app/entities/user-antwort/user-antwort.component';
import { UserAntwortService } from 'app/entities/user-antwort/user-antwort.service';
import { UserAntwort } from 'app/shared/model/user-antwort.model';

describe('Component Tests', () => {
  describe('UserAntwort Management Component', () => {
    let comp: UserAntwortComponent;
    let fixture: ComponentFixture<UserAntwortComponent>;
    let service: UserAntwortService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JpollTestModule],
        declarations: [UserAntwortComponent],
      })
        .overrideTemplate(UserAntwortComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserAntwortComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserAntwortService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserAntwort(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userAntworts && comp.userAntworts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
