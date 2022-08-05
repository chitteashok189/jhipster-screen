import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DosingDetailComponent } from './dosing-detail.component';

describe('Dosing Management Detail Component', () => {
  let comp: DosingDetailComponent;
  let fixture: ComponentFixture<DosingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DosingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ dosing: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DosingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DosingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load dosing on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.dosing).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
