import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiseaseControlDetailComponent } from './disease-control-detail.component';

describe('DiseaseControl Management Detail Component', () => {
  let comp: DiseaseControlDetailComponent;
  let fixture: ComponentFixture<DiseaseControlDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DiseaseControlDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ diseaseControl: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DiseaseControlDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DiseaseControlDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load diseaseControl on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.diseaseControl).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
