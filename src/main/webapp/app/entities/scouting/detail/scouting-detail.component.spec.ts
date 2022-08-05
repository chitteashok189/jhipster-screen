import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ScoutingDetailComponent } from './scouting-detail.component';

describe('Scouting Management Detail Component', () => {
  let comp: ScoutingDetailComponent;
  let fixture: ComponentFixture<ScoutingDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ScoutingDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ scouting: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ScoutingDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ScoutingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load scouting on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.scouting).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
