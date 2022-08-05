import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PlantFactoryControllerDetailComponent } from './plant-factory-controller-detail.component';

describe('PlantFactoryController Management Detail Component', () => {
  let comp: PlantFactoryControllerDetailComponent;
  let fixture: ComponentFixture<PlantFactoryControllerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlantFactoryControllerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ plantFactoryController: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PlantFactoryControllerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PlantFactoryControllerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load plantFactoryController on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.plantFactoryController).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
