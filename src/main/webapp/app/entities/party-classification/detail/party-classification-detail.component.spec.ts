import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyClassificationDetailComponent } from './party-classification-detail.component';

describe('PartyClassification Management Detail Component', () => {
  let comp: PartyClassificationDetailComponent;
  let fixture: ComponentFixture<PartyClassificationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyClassificationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyClassification: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyClassificationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyClassificationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyClassification on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyClassification).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
