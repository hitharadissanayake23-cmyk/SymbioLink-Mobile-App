package com.example.data

import com.example.model.AccountType
import com.example.model.Proposal
import com.example.model.ProposalStatus
import com.example.model.Requirement
import com.example.model.RequirementStatus
import com.example.model.Resource
import com.example.model.ServiceOffer
import com.example.model.ServiceTier
import com.example.model.User

/**
 * Student 2 & 3: Sample Data Provider
 * Provides realistic initial mock data for businesses, requirements, services, proposals, and SDG-17 resources.
 */
object SampleData {

    val currentUser = User(
        id = "user_001",
        fullName = "Kasun Fernando",
        businessName = "GreenTech Solutions",
        email = "kasun@greentech.lk",
        phone = "+94 77 123 4567",
        accountType = AccountType.MSME,
        industry = "Sustainable Technology & IT",
        location = "Colombo, Sri Lanka",
        memberSince = "January 2026",
        about = "GreenTech Solutions provides sustainable technology, clean IT infrastructure, and digital transformation services for growing businesses in Sri Lanka.",
        partnershipScore = 85,
        activeRequirementsCount = 2,
        serviceOffersCount = 3,
        submittedProposalsCount = 4,
        partnershipsCount = 4
    )

    val sampleRequirements = listOf(
        Requirement(
            id = "req_1",
            title = "Corporate Website & Portal Development",
            companyName = "ABC Holdings",
            category = "IT & Software",
            description = "Seeking a reliable MSME or tech partner to redesign our corporate web portal with responsive design, customer inquiry system, and ERP integration.",
            budget = "LKR 250,000",
            volume = "1 Portal",
            deadline = "25 Aug 2026",
            location = "Colombo, Sri Lanka",
            contactEmail = "procurement@abcholdings.lk",
            postedDate = "15 Aug 2026",
            proposalsCount = 6,
            status = RequirementStatus.ACTIVE
        ),
        Requirement(
            id = "req_2",
            title = "Eco-Friendly Cold Chain Logistics",
            companyName = "Green Lanka PLC",
            category = "Logistics",
            description = "Require temperature-controlled logistics and distribution support across Western and Central provinces for organic agricultural produce.",
            budget = "LKR 500,000",
            volume = "Weekly Deliveries",
            deadline = "30 Aug 2026",
            location = "Kurunegala, Sri Lanka",
            contactEmail = "supplychain@greenlanka.lk",
            postedDate = "14 Aug 2026",
            proposalsCount = 4,
            status = RequirementStatus.ACTIVE
        ),
        Requirement(
            id = "req_3",
            title = "Digital Marketing & Growth Campaign",
            companyName = "Oceanic Group",
            category = "Marketing",
            description = "Looking for an energetic marketing agency or freelancer to spearhead our social media branding, content creation, and search engine marketing for Q3-Q4.",
            budget = "LKR 180,000",
            volume = "3-Month Campaign",
            deadline = "01 Sep 2026",
            location = "Galle, Sri Lanka",
            contactEmail = "marketing@oceanicgroup.lk",
            postedDate = "12 Aug 2026",
            proposalsCount = 8,
            status = RequirementStatus.ACTIVE
        ),
        Requirement(
            id = "req_4",
            title = "Mobile App Development for Field Agents",
            companyName = "TechVision Lanka",
            category = "IT & Software",
            description = "Enterprise seeking an experienced mobile development team to create an offline-first Android field inspection and data collection app.",
            budget = "LKR 350,000",
            volume = "Android Application",
            deadline = "30 Aug 2026",
            location = "Colombo, Sri Lanka",
            contactEmail = "dev@techvision.lk",
            postedDate = "10 Aug 2026",
            proposalsCount = 5,
            status = RequirementStatus.ACTIVE
        ),
        Requirement(
            id = "req_5",
            title = "Sustainable Packaging Design & Rebranding",
            companyName = "Apex Manufacturing",
            category = "Design",
            description = "Need creative graphic and packaging designers to revamp our retail product containers using biodegradable materials and modern aesthetics.",
            budget = "LKR 220,000",
            volume = "5 Product Lines",
            deadline = "10 Sep 2026",
            location = "Biyagama, Sri Lanka",
            contactEmail = "design@apexmfg.lk",
            postedDate = "08 Aug 2026",
            proposalsCount = 3,
            status = RequirementStatus.ACTIVE
        ),
        Requirement(
            id = "req_6",
            title = "Energy Efficiency & Carbon Footprint Audit",
            companyName = "Green Lanka PLC",
            category = "Consulting",
            description = "Seeking accredited sustainability consultants to conduct a factory energy audit and provide roadmap for ISO 14064 carbon neutrality.",
            budget = "LKR 400,000",
            volume = "Full Facility Audit",
            deadline = "15 Sep 2026",
            location = "Colombo, Sri Lanka",
            contactEmail = "sustainability@greenlanka.lk",
            postedDate = "05 Aug 2026",
            proposalsCount = 2,
            status = RequirementStatus.ACTIVE
        )
    )

    val sampleServiceOffers = listOf(
        ServiceOffer(
            id = "srv_1",
            serviceName = "Full-Stack Web & Mobile App Development",
            businessName = "GreenTech Solutions",
            category = "IT & Software",
            description = "End-to-end custom application engineering using Kotlin, Jetpack Compose, Node.js, and Cloud architectures with continuous maintenance support.",
            startingPrice = "LKR 75,000",
            deliveryTime = "14 - 30 Days",
            experience = "5+ Years",
            contactEmail = "contact@greentech.lk",
            rating = 4.9,
            completedProjects = 24,
            basicTier = ServiceTier("Basic", "LKR 75,000", "Landing page or single module app with clean responsive layout"),
            standardTier = ServiceTier("Standard", "LKR 150,000", "Full multi-screen business app with API integration and offline database"),
            premiumTier = ServiceTier("Premium", "LKR 280,000", "Enterprise end-to-end portal with admin dashboard, cloud sync, and 1-year support")
        ),
        ServiceOffer(
            id = "srv_2",
            serviceName = "Complete Brand Identity & UI/UX Design",
            businessName = "CreativeHub Studio",
            category = "Design",
            description = "Distinctive visual branding, logo systems, design guidelines, and interactive mobile/web prototypes crafted in Figma.",
            startingPrice = "LKR 45,000",
            deliveryTime = "7 - 14 Days",
            experience = "4 Years",
            contactEmail = "hello@creativehub.lk",
            rating = 4.8,
            completedProjects = 38,
            basicTier = ServiceTier("Basic", "LKR 45,000", "Logo design + color palette + typography guide"),
            standardTier = ServiceTier("Standard", "LKR 85,000", "Full brand identity kit + social media templates + business stationery"),
            premiumTier = ServiceTier("Premium", "LKR 160,000", "Complete UI/UX design for web/mobile with design system and interactive prototype")
        ),
        ServiceOffer(
            id = "srv_3",
            serviceName = "Islandwide Express Delivery & Freight Logistics",
            businessName = "Lanka Logistics",
            category = "Logistics",
            description = "Fast, tracked B2B freight distribution, warehousing, and last-mile dispatch throughout all 9 provinces in Sri Lanka.",
            startingPrice = "LKR 25,000",
            deliveryTime = "1 - 3 Days",
            experience = "8 Years",
            contactEmail = "dispatch@lankalogistics.lk",
            rating = 4.7,
            completedProjects = 150,
            basicTier = ServiceTier("Basic", "LKR 25,000", "Single dispatch route up to 500kg"),
            standardTier = ServiceTier("Standard", "LKR 70,000", "Weekly dedicated vehicle distribution across Western province"),
            premiumTier = ServiceTier("Premium", "LKR 180,000", "Monthly full supply chain management with warehousing & tracking")
        ),
        ServiceOffer(
            id = "srv_4",
            serviceName = "B2B Performance Marketing & Lead Generation",
            businessName = "GrowthWave Lanka",
            category = "Marketing",
            description = "Data-driven B2B lead generation campaigns on LinkedIn, Google Search, and industry media to acquire high-value enterprise clients.",
            startingPrice = "LKR 60,000",
            deliveryTime = "30 Days",
            experience = "6 Years",
            contactEmail = "growth@growthwave.lk",
            rating = 4.9,
            completedProjects = 45,
            basicTier = ServiceTier("Basic", "LKR 60,000", "Search ads setup and campaign management for 1 month"),
            standardTier = ServiceTier("Standard", "LKR 120,000", "Multi-channel funnel (Google + LinkedIn) with custom landing pages"),
            premiumTier = ServiceTier("Premium", "LKR 220,000", "Full outbound B2B SDR campaign with verified decision-maker meetings")
        ),
        ServiceOffer(
            id = "srv_5",
            serviceName = "Sustainability & Carbon Footprint Consultancy",
            businessName = "EcoConsult Partners",
            category = "Consulting",
            description = "Expert advisory for UN SDG alignment, ESG reporting, green factory certifications, and energy efficiency reduction programs.",
            startingPrice = "LKR 90,000",
            deliveryTime = "21 Days",
            experience = "10 Years",
            contactEmail = "info@ecoconsult.lk",
            rating = 5.0,
            completedProjects = 32,
            basicTier = ServiceTier("Basic", "LKR 90,000", "Preliminary sustainability baseline audit"),
            standardTier = ServiceTier("Standard", "LKR 175,000", "Detailed carbon footprint roadmap & SDG 17 partnership readiness"),
            premiumTier = ServiceTier("Premium", "LKR 320,000", "Full ESG certification assistance and annual corporate compliance plan")
        )
    )

    val sampleProposals = listOf(
        Proposal(
            id = "prop_1",
            requirementId = "req_1",
            requirementTitle = "Corporate Website & Portal Development",
            enterpriseName = "ABC Holdings",
            quotation = "LKR 240,000",
            estimatedTimeline = "20 Days",
            proposalMessage = "Our team has extensive experience building enterprise portals with responsive Jetpack Web/Mobile stacks and robust API integrations. We can deliver on time with high security standards.",
            contactName = "Kasun Fernando",
            contactEmail = "kasun@greentech.lk",
            contactPhone = "+94 77 123 4567",
            attachedDocumentName = "GreenTech_Proposal_ABC.pdf",
            submittedDate = "16 Aug 2026",
            status = ProposalStatus.REVIEWED
        ),
        Proposal(
            id = "prop_2",
            requirementId = "req_2",
            requirementTitle = "Eco-Friendly Cold Chain Logistics",
            enterpriseName = "Green Lanka PLC",
            quotation = "LKR 480,000",
            estimatedTimeline = "25 Days",
            proposalMessage = "We partner with solar-powered refrigerated transport networks to guarantee 100% freshness while lowering emissions by 35% in line with SDG 17 goals.",
            contactName = "Kasun Fernando",
            contactEmail = "kasun@greentech.lk",
            contactPhone = "+94 77 123 4567",
            attachedDocumentName = "ColdChain_Specs.pdf",
            submittedDate = "15 Aug 2026",
            status = ProposalStatus.ACCEPTED
        ),
        Proposal(
            id = "prop_3",
            requirementId = "req_4",
            requirementTitle = "Mobile App Development for Field Agents",
            enterpriseName = "TechVision Lanka",
            quotation = "LKR 330,000",
            estimatedTimeline = "30 Days",
            proposalMessage = "We will implement an offline-first Android Compose application with local Room database synchronization and geo-tagged audit reports.",
            contactName = "Kasun Fernando",
            contactEmail = "kasun@greentech.lk",
            contactPhone = "+94 77 123 4567",
            attachedDocumentName = "FieldApp_Architecture.pdf",
            submittedDate = "17 Aug 2026",
            status = ProposalStatus.PENDING
        ),
        Proposal(
            id = "prop_4",
            requirementId = "req_3",
            requirementTitle = "Digital Marketing & Growth Campaign",
            enterpriseName = "Oceanic Group",
            quotation = "LKR 175,000",
            estimatedTimeline = "14 Days",
            proposalMessage = "Comprehensive 3-month omni-channel media strategy targeting regional B2B corporate events and luxury travel demographics.",
            contactName = "Kasun Fernando",
            contactEmail = "kasun@greentech.lk",
            contactPhone = "+94 77 123 4567",
            attachedDocumentName = "Marketing_Strategy_Doc.pdf",
            submittedDate = "13 Aug 2026",
            status = ProposalStatus.REJECTED
        )
    )

    val sampleResources = listOf(
        Resource(
            id = "res_1",
            title = "How to Build Strong B2B Partnerships",
            category = "Business Guides",
            description = "Proven strategies for MSMEs to approach large corporate enterprises, pitch synergies, and establish long-term trust.",
            fullContent = """
                Building enduring B2B relationships requires moving from a transactional mindset to a partnership mindset. 
                
                Key Strategies:
                1. Understand Enterprise Pain Points: Large firms prioritize reliability, compliance, and consistency over sheer cost savings.
                2. Standardize Deliverables: Provide structured SLAs, clear delivery milestones, and transparent weekly progress reporting.
                3. Demonstrate Shared Values: Aligning with sustainability (SDG goals) and local economic empowerment creates high emotional and strategic resonance with corporate boards.
                4. Maintain Open Communication: Regular stakeholder syncs prevent scope creep and solidify mutual goodwill.
            """.trimIndent(),
            readTime = "4 min read",
            iconType = "handshake"
        ),
        Resource(
            id = "res_2",
            title = "Writing a Winning Business Proposal",
            category = "Proposal Tips",
            description = "Step-by-step structure for drafting commercial proposals that win contracts and showcase value propositions.",
            fullContent = """
                A winning proposal is clear, persuasive, and directly addresses the client's explicit requirements.
                
                Proposal Structure:
                1. Executive Summary: Concisely summarize why your business is uniquely positioned to solve their problem.
                2. Scope of Work (SOW): Detail exact deliverables, phases, milestones, and testing procedures.
                3. Financial Quotation & Breakdown: Transparent pricing tiers (Basic, Standard, Premium) give clients purchasing autonomy.
                4. Timeline & Milestones: Realistic day-by-day or sprint-by-sprint estimates.
                5. Case Studies & References: Past client testimonials and verified project completions build instant credibility.
            """.trimIndent(),
            readTime = "5 min read",
            iconType = "description"
        ),
        Resource(
            id = "res_3",
            title = "Growing Your Small Business in Sri Lanka",
            category = "MSME Growth",
            description = "Actionable financial, digital, and operational tactics tailored for Sri Lankan micro, small, and medium enterprises.",
            fullContent = """
                MSMEs represent over 52% of Sri Lanka's GDP and provide 45% of total employment.
                
                Growth Accelerators:
                1. Digital Adoption: Transition from manual ledgers to cloud accounting and mobile B2B marketplace platforms like SymbioLink.
                2. Cashflow Management: Implement strict 30-day payment cycles and milestone-based advance invoicing.
                3. Quality Certifications: Obtaining ISO, GMP, or local SLSI standards allows MSMEs to bid for multi-million rupee enterprise tenders.
                4. Cluster Collaboration: Form consortiums with complementary service providers to bid jointly on massive enterprise RFPs.
            """.trimIndent(),
            readTime = "6 min read",
            iconType = "trending_up"
        ),
        Resource(
            id = "res_4",
            title = "Understanding SDG 17: Partnerships for the Goals",
            category = "Sustainable Business",
            description = "How cross-sector collaboration between large corporations and grassroots businesses drives sustainable development.",
            fullContent = """
                United Nations Sustainable Development Goal 17 seeks to strengthen global and local partnerships for sustainable development.
                
                SymbioLink's Role:
                - Inclusive Supply Chains: Connecting large enterprises with local MSMEs empowers local communities and reduces supply chain vulnerabilities.
                - Knowledge Sharing & Technology Transfer: Digital marketplace platforms democratize access to high-value tenders.
                - Transparent Governance: Standardized proposal submission and review criteria foster equitable economic opportunities for all entrepreneurs.
            """.trimIndent(),
            readTime = "4 min read",
            iconType = "public"
        ),
        Resource(
            id = "res_5",
            title = "Essential Digital Tools for Modern MSMEs",
            category = "Digital Skills",
            description = "Free and low-cost digital tools for productivity, communication, project management, and customer relations.",
            fullContent = """
                Leveraging the right software stack empowers lean teams to operate with the efficiency of large multinationals.
                
                Recommended Toolset:
                1. Project Management: Trello, Notion, or Asana for tracking sprint tasks and milestones.
                2. Cloud Collaboration: Google Workspace for shared documents, spreadsheets, and secure cloud backups.
                3. Invoicing & Inbound Payments: Digital payment gateways and automated invoice generators.
                4. Communication: Slack or WhatsApp Business for instant client customer service channels.
            """.trimIndent(),
            readTime = "3 min read",
            iconType = "laptop"
        )
    )
}
