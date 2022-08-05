import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ClimateDetailComponent } from './climate-detail.component';

describe('Climate Management Detail Component', () => {
  let comp: ClimateDetailComponent;
  let fixture: ComponentFixture<ClimateDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ClimateDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ climate: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ClimateDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ClimateDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load climate on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.climate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
