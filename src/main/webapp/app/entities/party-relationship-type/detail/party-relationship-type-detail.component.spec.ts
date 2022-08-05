import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyRelationshipTypeDetailComponent } from './party-relationship-type-detail.component';

describe('PartyRelationshipType Management Detail Component', () => {
  let comp: PartyRelationshipTypeDetailComponent;
  let fixture: ComponentFixture<PartyRelationshipTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyRelationshipTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyRelationshipType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyRelationshipTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyRelationshipTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyRelationshipType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyRelationshipType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
