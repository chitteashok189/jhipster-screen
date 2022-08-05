import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyRelationshipDetailComponent } from './party-relationship-detail.component';

describe('PartyRelationship Management Detail Component', () => {
  let comp: PartyRelationshipDetailComponent;
  let fixture: ComponentFixture<PartyRelationshipDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyRelationshipDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyRelationship: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyRelationshipDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyRelationshipDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyRelationship on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyRelationship).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
