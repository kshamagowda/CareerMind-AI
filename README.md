# CareerMind AI

**AI-powered career intelligence and placement copilot for students.**

CareerMind AI helps students understand their career readiness by analyzing resumes and job descriptions, identifying skill gaps, generating personalized learning roadmaps, and providing an AI-powered interview practice environment.

---

## 🚀 Features

### 📄 Resume Intelligence

- Upload a PDF resume
- Extract resume text automatically
- Detect technical skills from the resume
- Store extracted resume information in MySQL

### 💼 Job Intelligence

- Add job descriptions
- Automatically identify required technical skills
- Store job descriptions and extracted skills
- Compare job requirements with candidate skills

### 📊 Skill Gap Analysis

CareerMind AI compares the skills found in a resume with the skills required by a job.

It provides:

- Skill match percentage
- Matched skills
- Missing skills

### 🗺️ Personalized Learning Roadmap

Based on missing skills, CareerMind AI generates a learning roadmap containing:

- Learning duration
- Topics to study
- Practice projects
- Step-by-step learning progression

### 🎤 AI Interview Room

Practice interview questions for a selected role.

The system:

- Generates role-specific interview questions
- Accepts candidate answers
- Evaluates answers using AI
- Provides a score out of 100
- Provides strengths and improvement suggestions
- Generates a better-answer example
- Automatically moves to the next question

### 📚 Interview History

Previous interview attempts are stored in the database.

Students can review:

- Job title
- Interview question
- Score
- AI feedback
- Previous answers

### 📈 Interview Progress Analytics

CareerMind AI provides interview performance analytics:

- Total interviews
- Average score
- Best score
- Latest score

---

## 🧠 AI Integration

CareerMind AI uses **Ollama** for local AI-powered interview evaluation.

Current model:

```text
llama3.2:3b