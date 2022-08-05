import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlantFactoryDetailComponent } from './plant-factory-detail.component';

describe('PlantFactory Management Detail Component', () => {
  let comp: PlantFactoryDetailComponent;
  let fixture: ComponentFixture<PlantFactoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlantFactoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ plantFactory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlantFactoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlantFactoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load plantFactory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.plantFactory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
