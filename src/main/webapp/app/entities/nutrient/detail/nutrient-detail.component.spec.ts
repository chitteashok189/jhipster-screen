import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NutrientDetailComponent } from './nutrient-detail.component';

describe('Nutrient Management Detail Component', () => {
  let comp: NutrientDetailComponent;
  let fixture: ComponentFixture<NutrientDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NutrientDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ nutrient: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(NutrientDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(NutrientDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load nutrient on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.nutrient).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
