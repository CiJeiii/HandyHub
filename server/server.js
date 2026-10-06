require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');

const app = express();
app.use(express.json({ limit: '10mb' }));
app.use(cors());

// Connect to MongoDB Atlas or Local MongoDB using environment variable
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/handyhub';
mongoose.connect(MONGO_URI)
    .then(() => console.log('Successfully connected to MongoDB'))
    .catch(err => console.error('MongoDB connection error:', err));

// --- Mongoose Schemas & Models ---

const JobSchema = new mongoose.Schema({
    job_id: { type: Number, unique: true, required: true },
    employer_id: { type: String, required: true },
    title: { type: String, required: true },
    category: { type: String, required: true },
    description: { type: String, required: true },
    address_district: { type: String, required: true },
    preferred_date_time: { type: String, required: true },
    budget_php: { type: Number, required: true },
    status: { type: String, default: 'OPEN' },
    employer_phone: { type: String, default: '+639123456789' },
    employer_address: { type: String, default: 'Cagayan de Oro City' },
    problem_photo_urls: [String],
    created_at: { type: String, default: () => new Date().toISOString() }
});

const ApplicationSchema = new mongoose.Schema({
    application_id: { type: Number, unique: true, required: true },
    job_id: { type: Number, required: true },
    employee_id: { type: String, required: true },
    full_name: { type: String, required: true },
    profile_photo_url: { type: String, default: '' },
    star_rating: { type: Number, default: 4.9 },
    is_verified: { type: Boolean, default: true },
    proposal_message: { type: String, required: true },
    status: { type: String, default: 'PENDING' },
    unmasked_phone: { type: String, default: '+639987654321' },
    portfolio_image_urls: [String],
    skills: [String]
});

const Job = mongoose.model('Job', JobSchema);
const Application = mongoose.model('Application', ApplicationSchema);

// --- API Endpoints matching HandyHub ApiService.kt ---

// 1. Create Job Post (POST /api/jobs)
app.post('/api/jobs', async (req, res) => {
    try {
        const count = await Job.countDocuments();
        const newJobId = count + 1;

        const jobData = {
            job_id: newJobId,
            employer_id: req.body.employer_id || "EMP-OWNER-001",
            title: req.body.title,
            category: req.body.category,
            description: req.body.description,
            address_district: req.body.address_district,
            preferred_date_time: req.body.preferred_date_time,
            budget_php: req.body.budget_php,
            status: req.body.status || "OPEN",
            employer_phone: "+639123456789",
            employer_address: req.body.address_district + ", CDO",
            problem_photo_urls: req.body.problem_photos_base64 || []
        };

        const job = new Job(jobData);
        await job.save();
        res.status(201).json(job);
    } catch (error) {
        console.error('Error creating job:', error);
        res.status(500).json({ error: error.message });
    }
});

// 2. Get Employer Job Posts (GET /api/jobs/employer/{employerId})
app.get('/api/jobs/employer/:employerId', async (req, res) => {
    try {
        const jobs = await Job.find({ employer_id: req.params.employerId }).sort({ _id: -1 });
        res.json(jobs);
    } catch (error) {
        console.error('Error fetching employer jobs:', error);
        res.status(500).json({ error: error.message });
    }
});

// 3. Get Job Feed (GET /api/jobs/feed)
app.get('/api/jobs/feed', async (req, res) => {
    try {
        const { category } = req.query;
        let query = { status: 'OPEN' };
        if (category && category !== 'All') {
            query.category = category;
        }
        const jobs = await Job.find(query).sort({ _id: -1 });
        res.json(jobs);
    } catch (error) {
        console.error('Error fetching job feed:', error);
        res.status(500).json({ error: error.message });
    }
});

// 4. Get Job Applications (GET /api/jobs/{jobId}/applications)
app.get('/api/jobs/:jobId/applications', async (req, res) => {
    try {
        const jobIdNum = Number(req.params.jobId);
        let applications = await Application.find({ job_id: jobIdNum });

        // If no applications exist in DB yet for this job, return sample mock data for testing
        if (applications.length === 0) {
            applications = [
                {
                    application_id: jobIdNum * 100 + 1,
                    job_id: jobIdNum,
                    employee_id: "EMP-001",
                    full_name: "Juan Dela Cruz",
                    profile_photo_url: "https://via.placeholder.com/150",
                    star_rating: 4.8,
                    is_verified: true,
                    proposal_message: "I have 5 years of experience in this field and can start immediately.",
                    status: "PENDING",
                    unmasked_phone: "+639171234567",
                    portfolio_image_urls: [],
                    skills: ["Carpentry", "Repairs"]
                },
                {
                    application_id: jobIdNum * 100 + 2,
                    job_id: jobIdNum,
                    employee_id: "EMP-002",
                    full_name: "Maria Santos",
                    profile_photo_url: "https://via.placeholder.com/150",
                    star_rating: 4.95,
                    is_verified: true,
                    proposal_message: "Professional and reliable service guaranteed.",
                    status: "PENDING",
                    unmasked_phone: "+639189876543",
                    portfolio_image_urls: [],
                    skills: ["Plumbing"]
                }
            ];
        }

        res.json(applications);
    } catch (error) {
        console.error('Error fetching applications:', error);
        res.status(500).json({ error: error.message });
    }
});

// 5. Accept Application (POST /api/accept-application)
app.post('/api/accept-application', async (req, res) => {
    try {
        const { application_id, job_id } = req.body;

        // Update application status
        const updatedApp = await Application.findOneAndUpdate(
            { application_id: Number(application_id) },
            { status: 'ACCEPTED' },
            { new: true }
        );

        // Update corresponding job status to IN_PROGRESS
        await Job.findOneAndUpdate(
            { job_id: Number(job_id) },
            { status: 'IN_PROGRESS' }
        );

        res.json({
            status: "SUCCESS",
            message: "Application accepted successfully",
            unmasked_phone: updatedApp ? updatedApp.unmasked_phone : "+639123456789",
            unlock_id: 101
        });
    } catch (error) {
        console.error('Error accepting application:', error);
        res.status(500).json({ error: error.message });
    }
});

// Start Server
const PORT = process.env.PORT || 8000;
app.listen(PORT, () => {
    console.log(`HandyHub Backend Server running on http://localhost:${PORT}`);
});
