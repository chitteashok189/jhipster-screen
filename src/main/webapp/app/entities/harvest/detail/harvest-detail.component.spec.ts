import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HarvestDetailComponent } from './harvest-detail.component';

describe('Harvest Management Detail Component', () => {
  let comp: HarvestDetailComponent;
  let fixture: ComponentFixture<HarvestDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HarvestDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ harvest: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HarvestDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HarvestDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load harvest on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.harvest).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
