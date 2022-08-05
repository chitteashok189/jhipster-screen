import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyAttributeDetailComponent } from './party-attribute-detail.component';

describe('PartyAttribute Management Detail Component', () => {
  let comp: PartyAttributeDetailComponent;
  let fixture: ComponentFixture<PartyAttributeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyAttributeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyAttribute: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyAttributeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyAttributeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyAttribute on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyAttribute).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
