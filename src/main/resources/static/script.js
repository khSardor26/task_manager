// CONFIG
const API_BASE = "http://localhost:8080/api/v1/tasks";

const API = {
    getAll:     () => fetch(API_BASE),
    getOne:     id => fetch(`${API_BASE}/${id}`),
    create:     data => fetch(API_BASE, { method: "POST", headers: {"Content-Type": "application/json"}, body: JSON.stringify(data) }),
    update: (id, data) => fetch(`${API_BASE}/${id}`, { method: "PATCH", headers: {"Content-Type": "application/json"}, body: JSON.stringify(data) }),
    delete:     id => fetch(`${API_BASE}/${id}`, { method: "DELETE" })
};

// HELPERS
function formatDate(iso) {
    if (!iso) return "—";
    try {
        return new Date(iso).toLocaleDateString("en-GB", {
            day: "numeric", month: "short", year: "numeric"
        });
    } catch {
        return "Invalid date";
    }
}

function getPriorityClass(p) { return `priority-${(p || "MEDIUM").toLowerCase()}`; }
function getStatusClass(s) { return `status-${(s || "OPEN").toLowerCase()}`; }

// DOM
const els = {
    taskList:     document.getElementById("taskList"),
    emptyState:   document.getElementById("emptyState"),
    loading:      document.getElementById("loadingState"),
    modal:        document.getElementById("taskModal"),
    modalTitle:   document.getElementById("modalTitle"),
    form:         document.getElementById("taskForm"),
    editId:       document.getElementById("editId"),
    title:        document.getElementById("title"),
    desc:         document.getElementById("description"),
    dueDate:      document.getElementById("dueDate"),
    priority:     document.getElementById("priority"),
    status:       document.getElementById("status"),
    statusGroup:  document.getElementById("statusGroup"),
    btnSave:      document.getElementById("btnSave"),
};

// RENDER
function renderTasks(tasks) {
    els.taskList.innerHTML = "";
    els.emptyState.style.display = tasks.length === 0 ? "block" : "none";

    tasks.forEach(t => {
        const card = document.createElement("div");
        card.className = "task-card";
        card.innerHTML = `
      <h3 class="task-title">${t.title || "Untitled"}</h3>
      <p class="task-desc">${t.description || "No description provided"}</p>
      <div class="task-meta">
        <span class="badge ${getPriorityClass(t.priority)}">${t.priority}</span>
        <span class="badge ${getStatusClass(t.status)}">${t.status}</span>
        <span class="due-date">Due: ${formatDate(t.dueDate)}</span>
      </div>
      <div class="actions">
        <button class="btn btn-primary" onclick="openEditModal('${t.id}')">
          <i class="fa-solid fa-pen-to-square"></i> Edit
        </button>
        <button class="btn btn-danger" onclick="deleteTask('${t.id}')">
          <i class="fa-solid fa-trash-can"></i> Delete
        </button>
      </div>
    `;
        els.taskList.appendChild(card);
    });
}

// CORE FUNCTIONS
async function loadTasks() {
    els.loading.style.display = "block";
    els.taskList.innerHTML = "";
    els.emptyState.style.display = "none";

    try {
        const res = await API.getAll();
        if (!res.ok) {
            const text = await res.text();
            throw new Error(`Server error ${res.status}: ${text.slice(0,120)}...`);
        }
        const tasks = await res.json();
        renderTasks(Array.isArray(tasks) ? tasks : []);
    } catch (err) {
        console.error("Load failed:", err);
        alert("Error loading tasks:\n" + err.message);
    } finally {
        els.loading.style.display = "none";
    }
}

async function deleteTask(id) {
    if (!confirm("Delete this task permanently?")) return;
    try {
        const res = await API.delete(id);
        if (!res.ok) throw new Error(`Delete failed: ${res.status}`);
        loadTasks();
    } catch (err) {
        alert("Error deleting task:\n" + err.message);
    }
}

// MODAL LOGIC
function openCreateModal() {
    els.modalTitle.textContent = "Create New Task";
    els.btnSave.textContent = "Create Task";
    els.statusGroup.style.display = "none";
    els.form.reset();
    els.editId.value = "";
    els.modal.classList.add("show");
    els.title.focus();
}

async function openEditModal(id) {
    try {
        const res = await API.getOne(id);
        if (!res.ok) throw new Error("Cannot load task");
        const task = await res.json();

        els.modalTitle.textContent = "Edit Task";
        els.btnSave.textContent = "Save Changes";
        els.statusGroup.style.display = "block";

        els.editId.value      = task.id || "";
        els.title.value       = task.title || "";
        els.desc.value        = task.description || "";
        els.dueDate.value     = task.dueDate ? task.dueDate.split('T')[0] : ""; // Handle ISO to date input
        els.priority.value    = task.priority || "MEDIUM";
        els.status.value      = task.status   || "OPEN";

        els.modal.classList.add("show");
        els.title.focus();
    } catch (err) {
        alert("Cannot load task details:\n" + err.message);
    }
}

function closeModal() {
    els.modal.classList.remove("show");
}

// FORM SUBMIT
els.form.addEventListener("submit", async e => {
    e.preventDefault();

    const isEdit = !!els.editId.value;
    const payload = {
        title:       els.title.value.trim(),
        description: els.desc.value.trim() || null,
        dueDate:     els.dueDate.value     || null,
        priority:    els.priority.value,
    };

    if (isEdit) payload.status = els.status.value;

    if (!payload.title) return alert("Title is required");
    if (!payload.dueDate) return alert("Due date is required");

    try {
        const res = await (isEdit ? API.update(els.editId.value, payload) : API.create(payload));
        if (!res.ok) {
            const errText = await res.text();
            throw new Error(errText || `Failed with status ${res.status}`);
        }
        closeModal();
        loadTasks();
    } catch (err) {
        console.error(err);
        alert("Error saving task:\n" + err.message);
    }
});

// EVENT LISTENERS
document.getElementById("btnNewTask").onclick = openCreateModal;
document.getElementById("btnCancel").onclick  = closeModal;
document.getElementById("btnRefresh").onclick = loadTasks;

els.modal.addEventListener("click", e => {
    if (e.target === els.modal) closeModal();
});

document.addEventListener("keydown", e => {
    if (e.key === "Escape" && els.modal.classList.contains("show")) {
        closeModal();
    }
});

// Initial load
loadTasks();