import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DiseaseDetailComponent } from './disease-detail.component';

describe('Disease Management Detail Component', () => {
  let comp: DiseaseDetailComponent;
  let fixture: ComponentFixture<DiseaseDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DiseaseDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ disease: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DiseaseDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DiseaseDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load disease on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.disease).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
