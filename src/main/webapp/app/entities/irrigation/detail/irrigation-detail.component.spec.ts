import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IrrigationDetailComponent } from './irrigation-detail.component';

describe('Irrigation Management Detail Component', () => {
  let comp: IrrigationDetailComponent;
  let fixture: ComponentFixture<IrrigationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [IrrigationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ irrigation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(IrrigationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(IrrigationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load irrigation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.irrigation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
