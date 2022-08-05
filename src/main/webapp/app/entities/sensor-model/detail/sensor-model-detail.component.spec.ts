import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SensorModelDetailComponent } from './sensor-model-detail.component';

describe('SensorModel Management Detail Component', () => {
  let comp: SensorModelDetailComponent;
  let fixture: ComponentFixture<SensorModelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SensorModelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ sensorModel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SensorModelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SensorModelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load sensorModel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.sensorModel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
