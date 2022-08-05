import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PartyStatusItemDetailComponent } from './party-status-item-detail.component';

describe('PartyStatusItem Management Detail Component', () => {
  let comp: PartyStatusItemDetailComponent;
  let fixture: ComponentFixture<PartyStatusItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PartyStatusItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ partyStatusItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PartyStatusItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PartyStatusItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load partyStatusItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.partyStatusItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
