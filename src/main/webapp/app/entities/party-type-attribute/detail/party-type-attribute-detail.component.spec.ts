import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyTypeAttributeDetailComponent } from './party-type-attribute-detail.component';

describe('PartyTypeAttribute Management Detail Component', () => {
  let comp: PartyTypeAttributeDetailComponent;
  let fixture: ComponentFixture<PartyTypeAttributeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyTypeAttributeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyTypeAttribute: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyTypeAttributeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyTypeAttributeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyTypeAttribute on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyTypeAttribute).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
