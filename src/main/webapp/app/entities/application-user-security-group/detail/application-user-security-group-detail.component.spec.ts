import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ApplicationUserSecurityGroupDetailComponent } from './application-user-security-group-detail.component';

describe('ApplicationUserSecurityGroup Management Detail Component', () => {
  let comp: ApplicationUserSecurityGroupDetailComponent;
  let fixture: ComponentFixture<ApplicationUserSecurityGroupDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ApplicationUserSecurityGroupDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ applicationUserSecurityGroup: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ApplicationUserSecurityGroupDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ApplicationUserSecurityGroupDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load applicationUserSecurityGroup on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.applicationUserSecurityGroup).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
